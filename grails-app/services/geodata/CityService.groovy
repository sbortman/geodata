package geodata

import geoscript.GeoScript
import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.render.Map as GeoScriptMap
import geoscript.workspace.Workspace
import grails.transaction.Transactional

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import static geoscript.style.Symbolizers.*

@Transactional
class CityService
{
  def messageSource
  def dataSourceUnproxied

  enum RenderMethod {
    BLANK, GEOSCRIPT
  }

  def loadCSV(File csvFile)
  {
    csvFile.eachLine( 0 ) { line, i ->
      if ( i > 0 )
      {

        def record = line?.split( ',' )

        def city = new City(
            name: record[0],
            country: record[1],
            population: record[2]?.toInteger(),
            capital: record[3] == 'T',
            longitude: record[4]?.toDouble(),
            latitude: record[5]?.toDouble()
        )

        city.location = GeoScript.unwrap( new Point( city.longitude, city.latitude ) )
        city.location.setSRID( 4326 )

        if ( !city.save() )
        {
          city.errors.allErrors.each { println messageSource.getMessage( it, null ) }
        }

        //println "${city}"
      }
    }
  }

  def plotCities(def params)
  {
    println params

    def buffer = new ByteArrayOutputStream()
    def renderMethod = RenderMethod.GEOSCRIPT

    switch ( renderMethod )
    {
    case RenderMethod.BLANK:
      def image = new BufferedImage(
          params['WIDTH']?.toInteger(), params['HEIGHT']?.toInteger(),
          BufferedImage.TYPE_INT_ARGB )


      ImageIO.write( image, 'png', buffer )
      break
    case RenderMethod.GEOSCRIPT:
      Workspace.withWorkspace(
          dbtype: 'postgis',

          // All these can be blank (except for port for some reason)
          // The dataSource is provided by Hibernate.
          database: '',
          host: '',
          port: 5432,
          user: '',
          password: '',

          'Data Source': dataSourceUnproxied,
          'Expose primary keys': true
      ) { workspace ->

        def layer = workspace[params['LAYERS']]

        layer.style = shape( type: "star", size: 10, color: "#FF0000" )

        def map = new GeoScriptMap(
            width: params['WIDTH']?.toInteger(),
            height: params['HEIGHT']?.toInteger(),
            proj: params['SRS'],
            bounds: params['BBOX']?.split( ',' )*.toDouble() as Bounds,
            layers: [layer]
        )

        map.render( buffer )
        map.close()
      }
      break
    }

    [contentType: 'image/png', buffer: buffer.toByteArray()]
  }
}

package geodata

import com.vividsolutions.jts.geom.Point
import groovy.transform.ToString
import org.hibernate.spatial.GeometryType

@ToString(includeNames = true)
class City
{
  String name
  String country
  Integer population
  Boolean capital
  Double longitude
  Double latitude
  Point location

  static constraints = {
    name()
    country()
    population()
    capital()
    longitude()
    latitude()
  }

  static mapping = {
    location type: GeometryType, sqlType: 'geometry(Point, 4326)'
  }
}

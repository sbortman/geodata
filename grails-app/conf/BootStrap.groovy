import geodata.City

import org.geotools.factory.Hints

class BootStrap
{
  def cityService

  def init = { servletContext ->
    Hints.putSystemDefault( Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE )

    if ( City.count() == 0 )
    {
      cityService.loadCSV( 'cities.csv' as File )
    }
  }

  def destroy = {
  }
}

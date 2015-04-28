package geodata

class CityController
{
  def scaffold = true
  def cityService

  def plotCities()
  {
    def results = cityService.plotCities( params )

    render contentType: results.contentType, file: results.buffer
  }
}

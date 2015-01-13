import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import controllers.GameEngine
import controllers.Player
import controllers.Size

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  
  "A new game " should {
    val model = new GameEngine(new Size(8,8))

    "start with player 1" in {
      model.getPlayer must be_==(Player.One)
    }
  }
  /*"Application" should {

    "work from within a browser" in new WithBrowser {

      browser.goTo("http://localhost:" + port)

      browser.pageSource must contain("Hello Play Framework")
    }
  }*/
}

package controllers

import play.api._
import play.api.mvc.{ Action, Controller }
import play.api.data._
import play.api.data.Forms._
import play.api.data.Form
import play.api.data.Forms.tuple
import play.api.data.Forms.text
import play.api.libs.json._
import scala.collection.mutable.MutableList

case class UserData(name: String)

object Application extends Controller {

  var counter =0
  val model = new GameEngine()
  
  val formx = Form(
    tuple(
      "name" -> text,
      "scala_surname" -> text))

  def start = Action {
    Ok(views.html.index("Hello Play Framework"))
  }

  val gamepost = Form(
    tuple(
      "size" -> text,
      "starter" -> text))
      
  def restart = Action { implicit request =>

    def values = formx.bindFromRequest.data
    def size = values("size")
    def starter = values("starter")

    model.reset(new Size(8, 8), Player.One);

    
    Ok(views.html.game("Game Screen"+counter))
  }
  
  def move = Action { implicit request =>

    def values = formx.bindFromRequest.data
    def col = values("c")
    def row = values("r") 
    
    model.doMoveAt(new Position(col.toInt,row.toInt))
    
    Ok("["+getBoardList+"]") 
  }


  def load = Action {
    /*Ok(JsObject(
      Seq("t1"->Json.obj("row" ->1, "col" -> 2 , "val" ->1))
      ))*/
      // JsArray(ChatRoom.store.list.map(userToJson(_)))
	  
    /*
    var v1=Json.obj("r" ->1, "c" -> 2 , "v" ->1);
    
    Ok("["+v1+","+v1+"]")*/
    
    
    Ok("["+getBoardList+"]") 
  }
  
  def getBoardList = {
    var stones: List[String] = List()

    for (row <- 1 until 8; column <- 1 until 8) {
    	var value= model.getCellValue(new Position(column, row));
    	if(value>0){
    	  stones ::= Json.obj("r" ->row, "c" -> column , "v" ->value).toString
    	}
    }
    
    stones.mkString (", ")
  }

  def testindex = Action {
    Ok(views.html.index("Hello Play Framework"))
  }

  def index = Action {
    var x = new TesteMe().hans()
    
    Ok(views.html.menu(x))
  }

  def hello(name: String) = Action {
    Ok(views.html.index("Hello " + name))
  }

  def posthello() = Action(parse.tolerantFormUrlEncoded) { request =>
    val paramVal = request.body.get("name").map(_.head)
    paramVal map { _.toString } getOrElse ""

    Ok(views.html.hello("Hello " + (paramVal map { _.toString } getOrElse "")))
  }

  def postname = Action { implicit request =>

    def values = formx.bindFromRequest.data
    def name = values("name")

    Ok(views.html.hello("Hello: " + name))
  }

  /*
  def postname() = Action { request =>
   
   val userForm = Form(mapping("name" -> text)(UserData.apply)(UserData.unapply))

  userForm.bindFromRequest.fold(
  formWithErrors => {
    // binding failure, you retrieve the form containing errors:
    BadRequest(views.html.hello(" error "))
  },
  userData => {

    Ok(views.html.hello("Hello "+userData.name))
  }
)

  } */

  def form = Action {
    Ok(views.html.form("Show me the form"))
  }
}
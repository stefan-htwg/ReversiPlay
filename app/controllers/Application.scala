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

  val model = new GameEngine()
  
  val formx = Form(
    tuple(
      "name" -> text,
      "scala_surname" -> text))

  def start = Action {
    Ok(views.html.index("Hello Play Framework"))
  }
      
  def restart = Action { implicit request =>

    def values = formx.bindFromRequest.data
    def size = values("size")
    def starter = values("starter")

    //model.reset(new Size(8, 8), Player.One);
    
    Ok(views.html.game("Game Screen"))
  }
  
   def index = Action {
    var x = new TesteMe().hans()
    
    Ok(views.html.menu(x))
  }
   
  def move = Action { implicit request =>
    def values = formx.bindFromRequest.data
    
    model.doMoveAt(new Position(values("c").toInt,values("r").toInt))
    
   
   Ok(getGameData) 
  }

  def getGameData = {
    var score_p1 = model.getScoreFor(Player.One);
    var score_p2 = model.getScoreFor(Player.Two);
    var str = "["+getBoardList+"]";
   
    Json.obj("next"->model.getPlayer,"sp1"->score_p1,"sp2"->score_p2,"board"->str).toString
  }

  def load = Action {
   Ok(getGameData) 
  }
  
 
  
  def getBoardList = {
    var stones: List[String] = List()

    for (row <- 1 to 8; column <- 1 to 8) {
    	var value= model.getCellValue(new Position(column, row));
    	if(value>0){
    	  stones ::= Json.obj("r" ->row, "c" -> column , "v" ->value).toString
    	}
    }
    
    stones.mkString (", ")
  }
}
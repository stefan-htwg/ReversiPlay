package controllers

import scala.swing.Publisher
import scala.swing.event.Event


case class GameStateChanged() extends Event
case class BoardChanged() extends Event

class MyController(var engine: GameEngine) {

  def reset(col: Int, row: Int, startWithPlayer: Integer) {
   engine.reset(new Size(col, row), startWithPlayer)
   
   //publish(new BoardChanged)
   
  } 
  
  def reset = {
   engine.reset
   //publish(new BoardChanged)
  }
  
  def setValueAt(position: Position) {
      engine.doMoveAt(position)
     // publish(new GameStateChanged)
    }

  def getValueAt(position: Position) = engine.getCellValue(position)
  
  def getCurrentPlayer = engine getPlayer

  def getPlayer1Score = engine getScoreFor(Player One)

  def getPlayer2Score = engine getScoreFor(Player Two)
  
  def getGameStatus = engine getGameStatus

  override def toString = engine toString
}




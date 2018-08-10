package com.cdintelligent.iot.proxy

import akka.actor.{Actor, ActorLogging}
import com.cdintelligent.iot.proxy.ServerActor.MessageBuffer

class MessageExtract() extends Actor with ActorLogging {
  override def receive: Receive = {
    case MessageBuffer(buf) => {
      //消息转化为相应的json数据


    }
  }
}

object MessageExtract {

}

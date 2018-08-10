package com.cdintelligent.iot.proxy

import java.net.InetSocketAddress
import java.nio.ByteBuffer

import akka.actor.{Actor, ActorLogging, ActorSystem}
import com.cdintelligent.iot.proxy.ServerActor.StartService
import org.apache.mina.api.{AbstractIoHandler, IoSession}
import org.apache.mina.filter.logging.LoggingFilter
import org.apache.mina.transport.nio.NioTcpServer

import scala.collection.mutable


class ServerActor(actorSystem: ActorSystem) extends Actor with ActorLogging {

  val sessionMap: mutable.HashMap[String, IoSession] = mutable.HashMap.empty

  override def preStart(): Unit = {

  }

  def receive = {

    case StartService(port) => {
      serverStart(port)
    }

  }


  def serverStart(port: Int): Unit = {
    val acceptor = new NioTcpServer

    acceptor.setFilters(new LoggingFilter("LoggingFilter1"))


    acceptor.setIoHandler(new AbstractIoHandler() {
      override def sessionOpened(session: IoSession): Unit = {
        println(("session opened {}" + session))


      }

      override
      def messageReceived(session: IoSession, message: Any): Unit = {
        if (message.isInstanceOf[ByteBuffer]) {
          /**
            * 1、read接受包头信息
            * 2、判断是否注册包，验证注册
            * 3、判断是否为注册验证包,验证通过后 sessionMap+=(设备Id->session)
            *
            */

          // self !


        }
      }
    })

    val address = new InetSocketAddress(9999)
    acceptor.bind(address)

  }


}

object ServerActor {

  case class StartService(port: Int)

  case class MessageBuffer(message: ByteBuffer)

}

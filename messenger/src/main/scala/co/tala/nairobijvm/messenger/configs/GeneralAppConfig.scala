package co.tala.nairobijvm.messenger.configs

import com.typesafe.config.Config
import play.api.ConfigLoader

case class GeneralAppConfig(name: String)

object GeneralAppConfig {
  val configPath = "general"

  implicit val configLoader: ConfigLoader[GeneralAppConfig] = new ConfigLoader[GeneralAppConfig] {
    def load(rootConfig: Config, path: String): GeneralAppConfig = {
      val config = rootConfig.getConfig(path)
      GeneralAppConfig(
        name = config.getString("name")
      )
    }
  }
}


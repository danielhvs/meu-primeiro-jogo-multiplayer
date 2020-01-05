(ns multi-snake.server
    (:require
     [multi-snake.handler :refer [app]]
     [config.core :refer [env]]
     [taoensso.sente :as sente]
     [ring.adapter.jetty :refer [run-jetty]])
    (:gen-class))



(defn -main [& args]
  (let [port (or (env :port) 3000)]
    (run-jetty app {:port port :join? false})))

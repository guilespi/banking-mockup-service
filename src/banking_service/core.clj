(ns banking-service.core
  (:require [banking-service.server :as server]))

(defn -main
  "Start web server"
  [& args]
  (server/start))

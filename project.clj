(defproject banking-service "0.1.0-SNAPSHOT"
  :description "Infocorp Banking Service Stubs"
  :url "http://www.infocorpgroup.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring/ring-jetty-adapter "1.1.1"]
                 [compojure "1.1.1"]
                 [clj-http "0.6.3"]
                 [org.clojure/data.json "0.2.1"]
                 [slingshot "0.10.3"]
                 [org.clojure/tools.cli "0.2.2"]
                 [org.clojure/java.jdbc "0.0.6"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [midje "1.4.0"]]
  :main banking-service.core)

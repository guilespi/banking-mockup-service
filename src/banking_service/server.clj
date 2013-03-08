(ns banking-service.server
  (:require [ring.adapter.jetty :as jetty]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.util.response :as response]
            [clojure.data.json :as json])
  (:use ring.middleware.params
        ring.middleware.keyword-params
        ring.middleware.nested-params
        [compojure.core :only (GET PUT POST ANY defroutes context)]))

(defn app-files
  ""
  [directory]
  (let [files (file-seq (clojure.java.io/file directory))
        bank-files (filter #(re-matches #".+\.bank$" (.getName %)) files)]
    (json/write-str (map #(.getName %) bank-files))))

(defn- newsfeed
  []
  [{:id 1
    :title "Nacional 4 - Boca 0"
    :abstract "El bolso le come la cola al bostero"
    :content "Despues de varias jugadas magistrales del chino recoba.."
    :icon "http://localhost:8000/icons/01-refresh.png"
    :image ""}
   {:id 2
    :title "Finalmente Mujica aprende a hablar"
    :abstract "Despues de varias semanas de clases de espaniol, el presidente habla"
    :content "Aparentemente habria sido una maestra de jardinera que le dedico un rato libre para que el mandatario no se coma mas las eses"
    :icon ""
    :image nil}])

(defn- document-types
  []
  [{:id 1 :description "Cedula"}
   {:id 2 :description "Pasaporte"}
   {:id 3 :description "DNI"}])

(defn- get-user-products
  []
  [{:product-type "tarjeta"
    :product-name "Tarjeta de Credito VISA Internacional"
    :product-category "Tarjeta VISA"
    :card-number "5443 1231 2222 0226"
    :usd-debt "U$S 1.520,00"
    :uyp-debt "$ 8.930.560,20"}
   {:product-type "prestamo"
    :product-name "Prestamo Hipotecario 15 anios Tasa Fija"
    :product-category "Prestamos Vivienda"
    :loan-id "91812122521"
    :loan-ammount "$ 2.320.000,00"
    :paid-ammount "$ 16.000,00"
    :remaining-ammount "36/180"
    :next-due-date "VTO. 01/11/2011"}
   {:product-type "cuenta-corriente"
    :product-name "Cuenta Corriente Pesos Uruguayos"
    :product-category "Cuenta Cheques"
    :account-number "123123125071"
    :account-balance "$ 142.320.226,00"}
   {:product-type "plazo-fijo"
    :product-name "Plazo Fijo"
    :product-category "Ahorros para Universidad Pablo"
    :plazo-id "234234237560"
    :plazo-ammount "U$S 26.000,00"
    :due-date "Vencimiento 31/08/2013"
    :plazo-tasa "TASA 2,5%"}])

(defroutes main-routes
  (GET "/definitions" [] (app-files "resources/public"))
  (GET "/newsfeed" [] (-> (response/response (json/write-str (newsfeed)))
                          (response/content-type "application/json")))
  (GET "/login/documents" [] (-> (response/response (json/write-str (document-types)))
                                 (response/content-type "application/json")))
  (GET "/products" [] (-> (response/response (json/write-str (get-user-products)))
                          (response/content-type "application/json")))
  (route/resources "/")
  (route/not-found (do (println "Unknown url received")
                       "Page not found")))

(def app
  (-> main-routes
      wrap-keyword-params
      wrap-nested-params
      wrap-params))

(defn start
  []
    (println "Starting web server at http://localhost:8000/")
    (jetty/run-jetty app {:join? true :port 8000}))

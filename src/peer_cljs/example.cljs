(ns peer-cljs.example
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! <! timeout chan]]
            [peer-cljs.core :refer [peer connect accept send! receive]]))

(enable-console-print!)

(defn receive-sos 
  [my-api-key]
  (go (let [my-peer (<! (peer my-api-key))
            my-conn (do (println "My id is: " (:id my-peer))
                        (println "Waiting some requests...")
                        (<! (accept my-peer)))]
        (js/alert (str "Received " 
                       (<! (receive my-conn))
                       "!")))))

(defn send-sos 
  [my-api-key]
  (go (let [my-peer (<! (peer my-api-key))
            my-conn (<! (connect my-peer (js/prompt "Please Enter another-peer-id")))]
        (send! my-conn "SOS"))))

(defn on-js-reload []

)

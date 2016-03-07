(ns peer-cljs.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! <! timeout chan]]))

(defrecord Peer [id obj api-key])

(defrecord Connection [another-id obj c])

(defn- fetch-peer-id "Fetch peer-id by peer-obj (go block)" 
  [peer-obj]
  (go (let [c (chan)]
        (.on peer-obj "open" #(put! c %))
        (<! c))))

(defn peer
  "Create peer EDN (go block)"
  ([api-key]
    (go (let [peer-obj (js/Peer. #js {"key" api-key})]
          (->Peer (<! (fetch-peer-id peer-obj)) 
                  peer-obj 
                  api-key))))
  ([id host port path]
    (go (let [peer-obj (js/Peer. #js {"host" host
                                      "port" port
                                      "path" path})]
          (->Peer (<! (fetch-peer-id peer-obj)) 
                  peer-obj 
                  id)))))

(defn connect
  [peer id]
  "Connect to another peer id and
   Create peer EDN (go block)"
  (go (let [conn-obj (.. peer -obj (connect id))
            c (chan)]
        (.on conn-obj "data" #(put! c %))
        (->Connection id conn-obj c))))

(defn- fetch-conn
  [peer]
  (go (let [c (chan)]
        (.. peer -obj (on "connection" #(put! c %)))
        (<! c))))

(defn accept
  [peer]
  "Ready to be connected by another peer
   (go block)"
  (go (let [conn-obj (<! (fetch-conn peer))
            c (chan)]
        (.on conn-obj "data" #(put! c %))
        (->Connection (.-peer conn-obj) conn-obj c))))

(defn send!
  [conn data]
  (.. conn -obj (send data)))

(defn receive
  [conn]
  (go (<! (:c conn))))

;; Credit: Michael S. Klishin
;; http://clojureriak.info/articles/getting_started.html

(ns artifact.riak_tester
  (:require [clojurewerkz.welle.core    :as wc]
            [clojurewerkz.welle.buckets :as wb]
            [clojurewerkz.welle.kv      :as kv])
  (:import com.basho.riak.client.http.util.Constants))

;; connect to Raik node at default http://127.0.0.1:8098/riak
(wc/connect!)
(wb/create "people")

(let [bucket "accounts"
      key    "lambda_two"
      val    {:name "Nate" :age 27 :username key}]
  ;; stores data serialized as JSON
  (kv/store bucket key val :content-type Constants/CTYPE_JSON_UTF8)
  ;; fetches it back
  (kv/fetch bucket key))

(kv/store "people" "joe" {:name "Joe" :age 30} :content-type "application/clojure")
(kv/store "people" "jane" {:name "Jane" :age 32}
          :content-type "application/clojure"
          :links [{:bucket "people" :key "joe" :tag "friend"}])

(kv/store "people" "zellig" {:name "Jane" :age 62}
          :content-type "application/clojure"
          :links [{:bucket "people" :key "jane" :tag "friend"}])

(kv/store "people" "anna" {:name "Jane" :age 12}
          :content-type "application/clojure"
          :links [{:bucket "people" :key "jane" :tag "friend"}])

(kv/store "people" "doug" {:name "Jane" :age 22}
          :content-type "application/clojure"
          :links [{:bucket "people" :key "anna" :tag "friend"}])

(kv/store "people" "nate" {:name "Jane" :age 32}
          :content-type "application/clojure"
          :links [{:bucket "people" :key "joe" :tag "friend"}])

(kv/store "people" "matthew" {:name "Jane" :age 42}
          :content-type "application/clojure"
          :links [{:bucket "people" :key "doug" :tag "friend"}])

(kv/store "people" "john" {:name "Jane" :age 52}
          :content-type "application/clojure"
          :links [{:bucket "people" :key "anna" :tag "friend"}])

;; Other http requests:

;; ;; expensive
;; http://127.0.0.1:8098/riak/people?props=false&keys=true

;; ;; stream binary for efficient query
;; http://127.0.0.1:8098/riak/people?props=false&keys=stream

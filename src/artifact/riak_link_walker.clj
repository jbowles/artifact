(ns artifact.riak_link_walker
  (:use clojurewerkz.welle.links))


;; find friends from "freinds" bucket
;; http://127.0.0.1:8098/riak/people/jane/people,friend,0
(walk
  (start-at "people" "jane")
  (step     "people" "friend" true)
  (step     "people" "friend" false))

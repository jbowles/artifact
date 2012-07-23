(:use
  [twitter.oauth]
  [twitter.callbacks]
  [twitter.callbacks.handlers]
  [twitter.api.restful])



(def *creds* (make-oauth-creds "" "" "" ""))

(show-user :oauth-creds *creds* :params {:screen-name})


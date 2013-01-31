(ns fizz-buzz.core
  (:require (ring.util [response :as r]))
  (:require (compojure [core :as c]
                       [handler :as h]))
  (:require (ring.middleware [json :as j]))
  (:use [hiccup core form]))

(defn fizz-buzz
  [start end]
  (for [n (range start (+ end 1))]
    (cond (and (zero? (mod n 3))
               (zero? (mod n 5)))
          "FizzBuzz"
          
          (zero? (mod n 3))
          "Fizz"

          (zero? (mod n 5))
          "Buzz"
          
          :else
          n)))

;; fizz-buzz.core> (instance? clojure.lang.LazySeq (fizz-buzz 1 10))
;; true

(defn html-doc 
  [title & body] 
  (html 
   [:html 
    [:head 
     [:title title]] 
    [:body 
     [:div 
      [:h2 
       [:a {:href "/"} "Welcome to FizzBuzz!"]]]
     body]]))

(def fizz-buzz-form 
  (html-doc "Fizz... Buzz..." 
    (form-to [:post "/"] 
        "Start: " (text-field {:size 3} :start)
        "End: " (text-field {:size 3} :end)
        (submit-button "Get FizzBuzz!"))))

(c/defroutes fizz-buzz-routes
  (c/GET "/fizz-buzz" {params :params}
    (r/response {:result (fizz-buzz (Integer. (:start params))
                                    (Integer. (:end params)))}))
  (c/GET "/" []
    fizz-buzz-form)
  (c/POST "/" {params :params}
    (r/response {:result (fizz-buzz (Integer. (:start params))
                                    (Integer. (:end params)))})))

(def handler
  (-> (c/routes fizz-buzz-routes)
      (j/wrap-json-response)
      (h/site)))

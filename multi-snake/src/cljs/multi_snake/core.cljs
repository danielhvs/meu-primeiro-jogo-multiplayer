(ns multi-snake.core
  (:require
   [reagent.core :as reagent :refer [atom]]
))

;; -------------------------
;; Page components
(defn home-page []
  (fn []
    [:h1 "Welcome to multi-snake"]))

;; -------------------------
;; Initialize app
(defn init! []
  (reagent/render [home-page] 
                  (.getElementById js/document "app")))

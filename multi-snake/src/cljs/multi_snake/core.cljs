(ns multi-snake.core
  (:require
   [reagent.core :as reagent :refer [atom]]
))

(def scenario1
  [{:x 0 :y 0} {:x 1 :y 1} {:x 0 :y 1} {:x 1 :y 0}])

(defonce app-state (atom {:size 40 :scenario scenario1}))

(defn blank [p]
  [:rect
   {:width 0.95
    :height 0.95
    :fill "green"
    :x (+ 0.05 (:x p))
    :y (+ 0.05 (:y p))}])

(defn home-page []  
  [:div
   [:div
    [:center
     (let [width 2
           height 2
           ]
       (into [:svg
              {:view-box (str "0 0 " width " " height)
               :width (* (:size @app-state) width)
               :preserveAspectRatio "xMinYMin meet"
               :height (* (:size @app-state) height)}]
             (map blank (:scenario @app-state))))]]])

;; -------------------------
;; Initialize app
(defn init! []
  (reagent/render [home-page] 
                  (.getElementById js/document "app")))

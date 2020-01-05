(ns multi-snake.core
  (:require
   [reagent.core :as reagent :refer [atom]]
))

(defonce app-state (atom {:size 40}))

(def tam-tela 500)
(def tam-elemento (/ tam-tela 10))

(defn blank [x y]
  [:rect
   {:width tam-elemento
    :height tam-elemento
    :fill "yelllow"
    :x (* tam-elemento x)
    :y (* tam-elemento y)}])

(defn home-page []  
  (fn []
    [:div
     [:div
      [:center
       (into [:svg {:width tam-tela :height tam-tela}
              [:rect {:width tam-tela
                      :height tam-tela
                      :style {:fill "rgb(255,255,255)"
                              :stroke-width "3"
                              :stroke "rgb(0,0,0)" }
                      :x 0
                      :y 0}]
              (blank 0 0)]
             #_(map blank (:scenario @app-state)))]]]))

;; -------------------------
;; Initialize app
(defn init! []
  (reagent/render [home-page] 
                  (.getElementById js/document "app")))

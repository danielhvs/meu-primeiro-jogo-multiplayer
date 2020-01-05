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
    :fill "yellow"
    :x (* tam-elemento x)
    :y (* tam-elemento y)}])

(defn home-page []  
  (fn []
    [:div
     #_[:div
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
(defn mount-root []
  (reagent/render [home-page] 
                  (.getElementById js/document "app")))

(defn init! []
  (mount-root))

(mount-root)

(defn get-canvas-context-from-id
  "Gets the drawing context from the id of the canvas element.
   Actual context is in a map with the canvas element and some
   other info."
  [id]
  (let [canvas (.getElementById js/document id)]
    {:canvas canvas
     :width (.-width canvas)
     :height (.-height canvas)
     :ctx (.getContext canvas "2d")}))

(defn render-game []
  (.requestAnimationFrame js/window render-game)
  (let [context (:ctx (get-canvas-context-from-id "screen"))
        ]
    (set! (.-globalAlpha  context) 1)
    #_(.clearRect context 0 0 800 800)
    (set! (.-fillStyle  context) "#000000")
    (set! (.-globalAlpha  context) 0.2)
    (.fillRect context 20 20 10 10)
))

(render-game)





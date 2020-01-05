(ns multi-snake.core
  (:require
   [reagent.core :as reagent :refer [atom]]
))

(def app-state (atom 
                {:current-player "player1"
                 :players [
                           {:playerId "player1" :x 1 :y 1}
                           {:playerId "player2" :x 2 :y 3}
                           {:playerId "player3" :x 3 :y 4}]}))

(def tam-tela 500)
(def tam-elemento (/ tam-tela 10))

(defn blank [x y]
  [:rect
   {:width tam-elemento
    :height tam-elemento
    :fill "yellow"
    :x (* tam-elemento x)
    :y (* tam-elemento y)}])

(defn player-pos [p]
  (blank (:x p) (:y p)))

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
                      :y 0}]]
             (map player-pos (:players @app-state)))]]]))

(defn moves [chave]
  (or 
   (chave
    {:ArrowLeft (fn [e] (assoc e :x (dec (:x e))))
     :ArrowRight (fn [e] (assoc e :x (inc (:x e))))
     :ArrowDown (fn [e] (assoc e :y (inc (:y e))))
     :ArrowUp (fn [e] (assoc e :y (dec (:y e))))})
   (fn [e] e)))

(defn key-pressed [key]
  (let [
        players (:players @app-state)
        current (first (filter #(= (:current-player @app-state) (:playerId %)) players))
]
    (swap! app-state assoc :players 
           (into
            [((moves (keyword key)) current)]
            (remove #(= current %)) players)))
  (println (str "APP-STATE:" @app-state))
  (println key)
)

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [home-page] 
                  (.getElementById js/document "app")))

(defn init! []
  (mount-root)
  (.addEventListener js/document "keydown"
                     (fn [event] (key-pressed (.-key event)))))

; dev
(mount-root) 

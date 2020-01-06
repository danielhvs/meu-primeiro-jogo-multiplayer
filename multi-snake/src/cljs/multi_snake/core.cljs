(ns multi-snake.core
  (:require
   [reagent.core :as reagent :refer [atom]]))

(defonce app-state (atom 
                {:current-player "player1"
                 :fruits [
                          {:x 1 :y 2}
                          {:x 6 :y 2}
                          {:x 3 :y 3}
                          {:x 5 :y 6}]
                 :players [
                           {:playerId "player1" :x 1 :y 1}
                           {:playerId "player2" :x 2 :y 3}
                           {:playerId "player3" :x 3 :y 4}]}))

; dev
(swap! app-state assoc :current-player "player1")

(def tam-tela 500)
(def tam-elemento (/ tam-tela 10))

(defn blank [x y color]
  [:rect
   {:width tam-elemento
    :height tam-elemento
    :fill color
    :x (* tam-elemento x)
    :y (* tam-elemento y)}])

(defn fruit-pos [fruit]
  (blank (:x fruit) (:y fruit) "red"))

(defn player-pos [player]
  (blank (:x player) (:y player) (if (= (:current-player @app-state) (:playerId player)) 
                                    "yellow"
                                    "gray")))

(defn home-page []  
  (fn []
    [:div
     [:div
      [:center
       (into
        (into [:svg {:width tam-tela :height tam-tela}
               [:rect {:width tam-tela
                       :height tam-tela
                       :style {:fill "rgb(255,255,255)"
                               :stroke-width "3"
                               :stroke "rgb(0,0,0)" }
                       :x 0
                       :y 0}]]
              (map player-pos (:players @app-state)))
        (map fruit-pos (:fruits @app-state)))]]]))

(defn moves [chave]
  (or 
   (chave
    {:ArrowLeft (fn [e] (assoc e :x (dec (:x e))))
     :ArrowRight (fn [e] (assoc e :x (inc (:x e))))
     :ArrowDown (fn [e] (assoc e :y (inc (:y e))))
     :ArrowUp (fn [e] (assoc e :y (dec (:y e))))})
   (fn [e] e)))

(defn key-pressed [key]
  (let [players (:players @app-state)
        current (first (filter #(= (:current-player @app-state) (:playerId %)) players))]
    (swap! app-state assoc :players 
           (into
            [((moves (keyword key)) current)]
            (remove #(= current %)) players)))
  (println (str "APP-STATE:" @app-state))
  (println key))

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

(ns multi-snake.prod
  (:require [multi-snake.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)

(ns amazing-parking-lot.command.executor
  (:require [amazing-parking-lot.input.command-argument-option :as option]))

(defn create-parking-lot [arguments]
  (let [number-of-slots (first arguments)]
    (str "Created parking lot with " number-of-slots " slots")))

(def executors {"create_parking_lot" create-parking-lot})

(defn execute [command-arguments-option]
  (let [executor (executors (option/command command-arguments-option))
        result (executor (option/arguments command-arguments-option))]
    (option/create-valid-with-result command-arguments-option result)))

(ns amazing-parking-lot.command.executor
  (:require [amazing-parking-lot.input.command-argument-option :as option]
            [amazing-parking-lot.parking-lot.parking-lot :as parking-lot]))

(defn create-parking-lot [command-arguments]
  (let [number-of-slots (first (option/arguments command-arguments))
        parking-lot (parking-lot/create number-of-slots)
        message (str "Created parking lot with " number-of-slots " slots")]
    (option/create-valid-option command-arguments message parking-lot)))

(def executors {"create_parking_lot" create-parking-lot})

(defn execute [command-arguments-option]
  (let [executor (executors (option/command command-arguments-option))]
    (executor command-arguments-option)))

(ns amazing-parking-lot.command.executor
  (:require [amazing-parking-lot.input.command-argument-option :as option]
            [amazing-parking-lot.parking-lot.parking-lot :as parking-lot]))

(defn- create-parking-lot [command-arguments]
  (let [number-of-slots (first (option/arguments command-arguments))
        parking-lot (parking-lot/create number-of-slots)
        message (str "Created parking lot with " number-of-slots " slots")]
    (option/create-valid-option command-arguments message parking-lot)))

(defn- park [command-arguments]
  (let [licence-number (first (option/arguments command-arguments))
        color (second (option/arguments command-arguments))
        parking-lot (option/parking-lot command-arguments)
        response (parking-lot/park licence-number color parking-lot)
        message (:message response)
        parking-lot (:parking-lot response)]
    (option/create-valid-option command-arguments message parking-lot)))

(def executors {"create_parking_lot" create-parking-lot
                "park" park})

(defn execute [command-arguments-option]
  (let [executor (executors (option/command command-arguments-option))]
    (executor command-arguments-option)))

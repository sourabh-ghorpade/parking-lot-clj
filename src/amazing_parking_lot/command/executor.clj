(ns amazing-parking-lot.command.executor
  (:require [amazing-parking-lot.input.command-argument-option :as option]
            [amazing-parking-lot.parking-lot.parking-lot :as parking-lot]))

(defn create-parking-lot [arguments]
  (let [number-of-slots (first arguments)
        parking-lot (parking-lot/create number-of-slots)]
    [parking-lot
     (str "Created parking lot with " number-of-slots " slots")]))

(def executors {"create_parking_lot" create-parking-lot})

(defn execute [command-arguments-option]
  (let [executor (executors (option/command command-arguments-option))
        lot-and-return-val (executor (option/arguments command-arguments-option))
        result-option (option/create-valid-with-result command-arguments-option (last lot-and-return-val))]
    (option/create-valid-option-with-parking-lot result-option (first lot-and-return-val))))

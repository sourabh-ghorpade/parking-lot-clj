(ns amazing-parking-lot.command.executor
  (:require [amazing-parking-lot.input.command-argument-option :as option]
            [amazing-parking-lot.models.parking-lot :as parking-lot]
            [amazing-parking-lot.models.car :as car]
            [amazing-parking-lot.models.event :as event]))

(defn- create-parking-lot [command-arguments]
  (let [number-of-slots (first (option/arguments command-arguments))
        parking-lot (parking-lot/create (Integer/parseInt number-of-slots))
        message (str "Created parking lot with " number-of-slots " slots")]
    (option/create-valid-option command-arguments message parking-lot)))

(defn generate-park-message [response]
  (condp = (:response-code response)
    (event/status-codes :state-changed) (format "Parked in slot number %s" (get-in response [:action :slot-number]))
    (event/status-codes :parking-lot-full) "Parking Lot is full"))

(defn- park [command-arguments]
  (let [licence-number (first (option/arguments command-arguments))
        color (second (option/arguments command-arguments))
        car (car/create licence-number color)
        parking-lot (option/parking-lot command-arguments)
        response (parking-lot/park car parking-lot)
        message (generate-park-message response)
        parking-lot (:parking-lot response)]
    (option/create-valid-option command-arguments message parking-lot)))

(defn- leave [command-arguments]
  (parking-lot/leave (first (option/arguments command-arguments))
                     (option/parking-lot command-arguments)))

(def executors {"create_parking_lot" create-parking-lot
                "park"               park
                "leave"              leave})

(defn execute [command-arguments-option]
  (let [executor (executors (option/command command-arguments-option))]
    (executor command-arguments-option)))

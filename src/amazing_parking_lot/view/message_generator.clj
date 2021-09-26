(ns amazing-parking-lot.view.message-generator
  (:require [amazing-parking-lot.models.event :as event]
            [amazing-parking-lot.models.car :as car]))

(defn generate-message [response-code action]
  (condp = response-code
    (event/status-codes :car-parked) (format "Parked in slot number %s" (:slot-number action))
    (event/status-codes :parking-lot-full) "Parking Lot is full"
    (event/status-codes :car-un-parked) (format "Un-parked car %s at slot %s" (car/get-license-number (:car action)) (:slot-number action))
    (event/status-codes :registration-numbers-for-cars-with-colour) (clojure.string/join ", " (:query-result action))))

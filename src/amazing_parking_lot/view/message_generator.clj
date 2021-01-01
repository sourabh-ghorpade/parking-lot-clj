(ns amazing-parking-lot.view.message-generator
  (:require [amazing-parking-lot.models.event :as event]))


(defn generate-message [response-code action]
  (condp = response-code
    (event/status-codes :car-parked) (format "Parked in slot number %s" (:slot-number action))
    (event/status-codes :parking-lot-full) "Parking Lot is full"))
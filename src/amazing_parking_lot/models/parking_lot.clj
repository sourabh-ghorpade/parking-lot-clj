(ns amazing-parking-lot.models.parking-lot
  (:require [amazing-parking-lot.models.car :as car]
            [amazing-parking-lot.models.event :as event]))

(defn create [number-of-slots]
  {:number-of-slots number-of-slots
   :slots           (vec (repeat number-of-slots nil))})

;TODO Find better way to do this in clj
(defn remove-car [slot-number parking-lot]
  (let [coll (:slots parking-lot)
        i slot-number
        empty-slot [nil]]
    (concat (subvec coll 0 i)
            empty-slot
            (subvec coll (inc i)))))

(defn leave [slot-number parking-lot]
  (let [formatted-slot-number (dec (Integer/parseInt slot-number))
        car-to-be-removed (.get (:slots parking-lot) formatted-slot-number)]
    (if-not car-to-be-removed
      {:message     "No car parked at the given slot"
       :parking-lot parking-lot}
      (let [updated-parking-lot (assoc parking-lot :slots (remove-car formatted-slot-number parking-lot))
            licence-number (car/get-license-number car-to-be-removed)]
        {:message     (format "Un-Parked Car %s at slot %s" licence-number slot-number)
         :parking-lot updated-parking-lot}))))

(defn park [car parking-lot]
  (let [free-slot-number (.indexOf (:slots parking-lot) nil)
        car-not-found-return-code -1]
    (if-not (= free-slot-number car-not-found-return-code)
      (let [index-one-slot-number (inc free-slot-number)
            parking-lot (assoc parking-lot :slots (assoc (parking-lot :slots) free-slot-number car))
            event-message (event/create-car-parked-event :park-car index-one-slot-number car parking-lot)]
        event-message)
      (event/create-no-operation-event (event/status-codes :parking-lot-full) parking-lot))))

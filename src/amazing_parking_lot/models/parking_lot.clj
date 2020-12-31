(ns amazing-parking-lot.models.parking-lot
  (:require [amazing-parking-lot.models.car :as car]))

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
  (let [formatted-slot-number (- (Integer/parseInt slot-number) 1)
        car-to-be-removed (.get (:slots parking-lot) formatted-slot-number)
        updated-parking-lot (assoc parking-lot :slots (remove-car formatted-slot-number parking-lot))
        licence-number (car/get-license-number car-to-be-removed)]
    {:message     (format "Un-Parked Car %s at slot %s" licence-number slot-number)
     :parking-lot updated-parking-lot}))

(defn park [car parking-lot]
  (let [free-slot-number (.indexOf (:slots parking-lot) nil)
        car-not-found-return-code -1]
    (if-not (= free-slot-number car-not-found-return-code)
      {:message     (str "Parked in slot number " (inc free-slot-number))
       :parking-lot (assoc parking-lot :slots (assoc (parking-lot :slots) free-slot-number car))}
      {:message     "Parking Lot is full"
       :parking-lot parking-lot})))

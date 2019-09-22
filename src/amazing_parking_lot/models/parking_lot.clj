(ns amazing-parking-lot.models.parking-lot)

(defn create [number-of-slots]
  {:number-of-slots number-of-slots
   :slots           (vec (repeat number-of-slots nil))})

(defn park [car parking-lot]
  (let [free-slot-number (.indexOf (:slots parking-lot) nil)
        slots-with-new-car (assoc (parking-lot :slots) free-slot-number car)]
    {:message     (str "Parked in slot number " (+ free-slot-number 1))
     :parking-lot (assoc parking-lot :slots slots-with-new-car)}))

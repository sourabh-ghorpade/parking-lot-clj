(ns amazing-parking-lot.models.parking-lot)

(defn create [number-of-slots]
  {:number-of-slots number-of-slots})

(defn park [licence-number color parking-lot]
  {:message     "Parked in slot number 1"
   :parking-lot (assoc parking-lot :slots [{:licence-number licence-number
                                            :color          color}])})

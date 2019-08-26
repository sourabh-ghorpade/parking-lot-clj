(ns amazing-parking-lot.models.parking-lot)

(defn create [number-of-slots]
  {:number-of-slots number-of-slots
   :slots           [{} {} {}]})

(defn park [car parking-lot]
  {:message     "Parked in slot number 1"
   :parking-lot (assoc parking-lot :slots [car])})

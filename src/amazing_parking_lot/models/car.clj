(ns amazing-parking-lot.models.car)

(defn create [licence-number color]
  {
   :color          color
   :license-number licence-number
   })

(defn get-license-number [car]
  (:license-number car))

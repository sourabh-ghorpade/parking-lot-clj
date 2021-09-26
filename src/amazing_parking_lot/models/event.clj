(ns amazing-parking-lot.models.event)

; Needs to be refactored, either as def per code or to just use the keyword.
(def status-codes
  {:car-parked       2
   :parking-lot-full 1
   :car-un-parked    3
   :car-not-found 4
   :registration-numbers-for-cars-with-colour 5
   :slot-numbers-for-cars-with-colour 6
   })

(defn create-car-parked-event [action-name slot-number parked-car parking-lot]
  {:response-code (status-codes :car-parked)
   :action        {:name        action-name
                   :slot-number slot-number
                   :car         parked-car}
   :parking-lot   parking-lot})

(defn create-no-operation-event [response-code parking-lot]
  {:response-code response-code
   :action        {:name :no-operation}
   :parking-lot   parking-lot})

(defn create-car-un-parked-event [slot-number parked-car parking-lot]
  {:response-code (status-codes :car-un-parked)
   :action        {:name        :car-un-parked
                   :slot-number slot-number
                   :car         parked-car}
   :parking-lot   parking-lot})

(defn create-registration-for-color-event [query-result parking-lot]
  {:response-code (status-codes :registration-numbers-for-cars-with-colour)
   :action        {:name        :registration-numbers-for-cars-with-colour
                   :query-result query-result}
   :parking-lot   parking-lot})

(defn slot-numbers-for-cars-with-colour [query-result parking-lot]
  {:response-code (status-codes :slot-numbers-for-cars-with-colour)
   :action        {:name        :slot-numbers-for-cars-with-colour
                   :query-result query-result}
   :parking-lot   parking-lot})
(ns amazing-parking-lot.models.event)

; Needs to be refactored, either as def per code or to just use the keyword.
(def status-codes
  {:car-parked       2
   :parking-lot-full 1})

(defn create-car-parked-event [action-name slot-number parked-car parking-lot]
  {:response-code (status-codes :car-parked)
   :action        {:name        action-name
                   :slot-number slot-number
                   :car         parked-car}
   :parking-lot   parking-lot})

(defn create-no-operation-event [response-code]
  {:response-code response-code
   :action        {:name :no-operation}})
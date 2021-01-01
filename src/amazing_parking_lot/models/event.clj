(ns amazing-parking-lot.models.event)

(def status-codes
  {:car-parked       2
   :parking-lot-full 1})

(defn create-car-parked-event [action-name slot-number car]
  {:response-code (status-codes :car-parked)
   :action        {:name        action-name
                   :slot-number slot-number
                   :car         car}})

(defn create-no-operation-event [response-code]
  {:response-code response-code
   :action        {:name :no-operation}})
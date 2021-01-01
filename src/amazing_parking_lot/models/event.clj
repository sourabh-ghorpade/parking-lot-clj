(ns amazing-parking-lot.models.event)

(def status-codes
  {:state-changed    2
   :parking-lot-full 1})

(defn create-state-changed-event [action-name slot-number car]
  {:response-code (status-codes :state-changed)
   :action        {:name        action-name
                   :slot-number slot-number
                   :car         car}})

(defn create-no-operation-event [response-code]
  {:response-code response-code
   :action        {:name :no-operation}})
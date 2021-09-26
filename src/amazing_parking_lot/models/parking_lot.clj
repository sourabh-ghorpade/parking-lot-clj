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
    (vec (concat (subvec coll 0 i)
                 empty-slot
                 (subvec coll (inc i))))))

(defn leave [slot-number parking-lot]
  (let [formatted-slot-number (dec (Integer/parseInt slot-number))
        car-to-be-removed (.get (:slots parking-lot) formatted-slot-number)]
    (if-not car-to-be-removed
      (event/create-no-operation-event (event/status-codes :car-not-found) parking-lot)
      (let [updated-parking-lot (assoc parking-lot :slots (remove-car formatted-slot-number parking-lot))]
        (event/create-car-un-parked-event slot-number car-to-be-removed updated-parking-lot)))))

(defn park [car parking-lot]
  (let [free-slot-number (.indexOf (:slots parking-lot) nil)
        car-not-found-return-code -1]
    (if (= free-slot-number car-not-found-return-code)
      (event/create-no-operation-event (event/status-codes :parking-lot-full) parking-lot)
      (let [index-one-slot-number (inc free-slot-number)
            parking-lot-with-new-car (assoc parking-lot :slots (assoc (parking-lot :slots) free-slot-number car))]
        (event/create-car-parked-event :park-car index-one-slot-number car parking-lot-with-new-car)))))

(defn registration-numbers-for-cars-with-colour [required-color parking-lot]
  (let [filtered-cars (filter (fn [car] (= (car/color car) required-color)) (:slots parking-lot))
        registration-numbers (map car/get-license-number filtered-cars)]
    (event/create-query-processed-event registration-numbers parking-lot)))

(defn slot-numbers-for-cars-with-colour [required-color parking-lot]
  (let [slots (keep-indexed #(if (= (car/color %2) required-color) %1) (:slots parking-lot))
        index-one-slots (map inc slots)]
    (event/create-query-processed-event index-one-slots parking-lot)))

(defn slot-number-for-registration-number [registration-number parking-lot]
  (let [index-one-slot-number (inc (first (keep-indexed #(if (= (car/get-license-number %2) registration-number) %1)
                                              (:slots parking-lot))))]
    (event/create-query-processed-event [index-one-slot-number] parking-lot)))

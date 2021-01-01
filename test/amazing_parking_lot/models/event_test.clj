(ns amazing-parking-lot.models.event-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.models.event :refer :all]
            [amazing-parking-lot.models.car :as car]))

(deftest create-no-operation-event-test
  (testing "it returns a no-op event"
    (is (= (create-no-operation-event (status-codes :parking-lot-full) :some-parking-lot)
           {:response-code 1
            :action        {:name :no-operation}
            :parking-lot   :some-parking-lot}))))

(deftest create-state-changed-event-test
  (testing "it returns a car parked event"
    (let [car (car/create "A" "White")]
      (is (= (create-car-parked-event :park 1 car :some-parking-lot)
             {:response-code 2
              :action        {:name        :park
                              :slot-number 1
                              :car         car}
              :parking-lot   :some-parking-lot})))))

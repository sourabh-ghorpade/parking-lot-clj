(ns amazing-parking-lot.view.message-generator-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.view.message-generator :refer [generate-message]]
            [amazing-parking-lot.models.event :as event]))

(deftest generate-message-test
  (testing "when the response code is for car parked"
    (testing "it generates car parked message"
      (let [generated-message (generate-message (event/status-codes :car-parked) {:slot-number 1})]
        (is (= "Parked in slot number 1" generated-message)))))
  (testing "when the response code is for parking-lot-full"
    (testing "it generates parking lot full message"
      (let [generated-message (generate-message (event/status-codes :parking-lot-full) nil)]
        (is (= "Parking Lot is full" generated-message))))))

(ns amazing-parking-lot.parking-lot.parking-lot-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.parking-lot.parking-lot :refer [create]]))

(deftest create-test
  (testing "creates a parking lot with the given slots"
    (is (= (create 6) {:number-of-slots 6}))))

(ns amazing-parking-lot.models.car-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.models.car :refer [create]]))

(deftest create-test
  (testing "it creates a car"
    (let [car (create "ABC" "White")]
      (is (= (:color car) "White"))
      (is (= (:license-number car) "ABC")))))

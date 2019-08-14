(ns parking-lot.input.parser-test
  (:require [clojure.test :refer :all])
  (:require [parking-lot.input.parser :refer :all]))

(deftest parse-test
  (testing "when the input has a command and arguments"
    (testing "it returns the an option with the command and arguments separated"
      (let [input "create_parking_lot 6"
            result (parse input)]
        (is (true? (:valid? result)))
        (is (= (:command result) "create_parking_lot"))
        (is (= (:arguments result) ["6"])))))

  (testing "when the input is nil"
    (testing "it returns the an option with valid false"
      (is (false? (:valid? (parse nil)))))))

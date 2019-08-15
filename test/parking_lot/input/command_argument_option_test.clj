(ns parking-lot.input.command-argument-option-test
  (:require [clojure.test :refer :all]
            [parking-lot.input.command-argument-option :refer :all]))

(deftest if-valid-test
  (testing "when the option is valid"
    (testing "it calls the function with the option and returns the result"
      (let [test-fn (constantly true)
            valid-option (create-valid-option "some-command" "23" "result")
            result (if-valid valid-option test-fn)]
        (is (true? result)))))

  (testing "when the option is invalid"
    (testing "it returns the invalid option"
      (let [test-fn (constantly true)
            invalid-option (create-invalid-option "some-result")
            result (if-valid invalid-option test-fn)]
        (is (= result invalid-option))))))

(ns amazing-parking-lot.input.command-argument-option-test
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.input.command-argument-option :refer :all]
            [amazing-parking-lot.input.command-argument-option :as option]))

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

(deftest create-valid-with-result-test
  (testing "returns an option using the existing option and the provided result"
    (let [option (create-valid-option "some-command" ["23"] "")
          new-option (create-valid-with-result option "new-result")]
      (is (= (option/result new-option) "new-result")))))

(deftest result-test
  (testing "returns the result of the option"
    (let [option (create-valid-option "some-command" ["23"] "result")]
      (is (= (option/result option) "result")))))

(deftest command-test
  (testing "returns the command of the option"
    (let [option (create-valid-option "some-command" ["23"] "result")]
      (is (= (option/command option) "some-command")))))

(deftest arguments-test
  (testing "returns the arguments of the option"
    (let [option (create-valid-option "some-command" ["23"] "result")]
      (is (= (option/arguments option) ["23"])))))

(deftest create-valid-option-with-parking-lot-test
  (testing "returns the parking lot of the option"
    (let [option (create-valid-option-with-parking-lot (create-valid-option "some-command" ["23"] "result")
                                                       {:slots 10})]
      (is (= (option/parking-lot option) {:slots 10})))))

(deftest parking-lot-test
  (testing "returns the parking lot of the option"
    (let [option {:command   command
                  :arguments arguments
                  :result    result
                  :valid?    true
                  :parking-lot {:slots 10}}]
      (is (= (option/parking-lot option) {:slots 10})))))

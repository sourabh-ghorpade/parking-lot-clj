(ns amazing-parking-lot.input.command-argument-option-test
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.input.command-argument-option :refer :all]
            [amazing-parking-lot.input.command-argument-option :as option]))

(deftest if-valid-test
  (testing "when the option is valid"
    (testing "it calls the function with the option and returns the result"
      (let [test-fn (constantly true)
            valid-option (create-valid-option "some-command" "23")
            result (if-valid valid-option test-fn)]
        (is (true? result)))))

  (testing "when the option is invalid"
    (testing "it returns the invalid option"
      (let [test-fn (constantly true)
            invalid-option (create-invalid-option "some-message")
            result (if-valid invalid-option test-fn)]
        (is (= result invalid-option))))))

(deftest message-test
  (testing "returns the message of the option"
    (let [option {:command   command
                  :arguments arguments
                  :message   "some message"
                  :valid?    true}]
      (is (= (option/message option) "some message")))))

(deftest command-test
  (testing "returns the command of the option"
    (let [option (create-valid-option "some-command" ["23"])]
      (is (= (option/command option) "some-command")))))

(deftest arguments-test
  (testing "returns the arguments of the option"
    (let [option (create-valid-option "some-command" ["23"])]
      (is (= (option/arguments option) ["23"])))))

(deftest create-valid-option-with-parking-lot-test
  (testing "returns the parking lot of the option"
    (let [option (create-valid-option-with-parking-lot (create-valid-option "some-command" ["23"])
                                                       {:slots 10})]
      (is (= (option/parking-lot option) {:slots 10})))))

(deftest parking-lot-test
  (testing "returns the parking lot of the option"
    (let [option {:command     command
                  :arguments   arguments
                  :message     message
                  :valid?      true
                  :parking-lot {:slots 10}}]
      (is (= (option/parking-lot option) {:slots 10})))))

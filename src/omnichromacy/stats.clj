(ns omnichromacy.stats
  (:require [clojure.core.matrix.stats :refer [mean variance sd sum]]
            [clojure.core.matrix :refer [transpose]]))

(defn band-stats
  "Given a spectral matrix, calculates descriptive stats for each band and
  returns a map containing outcomes by band (in vector)."
  [spec-mat]
  (let [by-band (transpose spec-mat)]
    {:mean (mean by-band)
     :variance (variance by-band)
     :stddev (sd by-band)}))

(defn mean-albedo
  "Given a spectral matrix, calculates the mean albedo (across all bands) for
  each pixel."
  [spec-mat]
  (mean spec-mat))

(defn sum-albedo
  "Given a spectral matrix, calculates the sum albedo (across all bands) for each
  pixel."
  [spec-mat]
  (sum spec-mat))

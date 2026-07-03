package com.tutor_buddy.service;

import com.tutor_buddy.model.Tutor;
import com.tutor_buddy.model.TutorBST;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TutorService {
    private TutorBST tutorBST;

    public TutorService() {
        this.tutorBST = new TutorBST();
    }

    public Tutor registerTutor(Tutor tutor) {
        tutor.setStatus("pending");
        tutorBST.insert(tutor);
        return tutor;
    }

    public List<Tutor> getAllTutors() {
        return tutorBST.getAllTutors();
    }

    public Tutor getTutorById(Long id) {
        Tutor tutor = tutorBST.find(id);
        if (tutor == null) {
            throw new RuntimeException("Tutor not found");
        }
        return tutor;
    }

    public Tutor updateTutor(Long id, Tutor updatedTutor) {
        Tutor tutor = tutorBST.find(id);
        if (tutor == null) {
            throw new RuntimeException("Tutor not found");
        }
        tutor.setFullName(updatedTutor.getFullName());
        tutor.setEmail(updatedTutor.getEmail());
        tutor.setPhone(updatedTutor.getPhone());
        tutor.setHourlyRate(updatedTutor.getHourlyRate());
        tutor.setBio(updatedTutor.getBio());
        tutor.setEducation(updatedTutor.getEducation());
        tutor.setExperienceYears(updatedTutor.getExperienceYears());
        tutor.setSubjects(updatedTutor.getSubjects());
        tutor.setAvailability(updatedTutor.getAvailability());
        tutor.setStatus(updatedTutor.getStatus());
        tutor.setProfilePicture(updatedTutor.getProfilePicture());
        tutor.setRating(updatedTutor.getRating());
        tutor.setSubjectExpertise(updatedTutor.getSubjectExpertise());
        tutorBST.update(tutor);
        return tutor;
    }

    public void deleteTutor(Long id) {
        tutorBST.delete(id);
    }

    public List<Tutor> getTutorsBySubject(String subject) {
        return tutorBST.getTutorsBySubject(subject);
    }

    public List<Tutor> getTutorsByStatus(String status) {
        return tutorBST.getTutorsByStatus(status);
    }

    /**
     * Filters tutors based on expertise, experience years, and rating
     */
    public List<Tutor> filterTutors(Boolean hasExpertise, Integer minExperienceYears, Double minRating) {
        List<Tutor> allTutors = getAllTutors();
        List<Tutor> filteredTutors = new ArrayList<>();
        
        for (Tutor tutor : allTutors) {
            boolean meetsExpertiseCriteria = hasExpertise == null || tutor.getSubjectExpertise() == hasExpertise;
            boolean meetsExperienceCriteria = minExperienceYears == null || tutor.getExperienceYears() >= minExperienceYears;
            boolean meetsRatingCriteria = minRating == null || tutor.getRating() >= minRating;
            
            if (meetsExpertiseCriteria && meetsExperienceCriteria && meetsRatingCriteria) {
                filteredTutors.add(tutor);
            }
        }
        
        return filteredTutors;
    }

    /**
     * Sorts tutors by their subject expertise
     */
    public List<Tutor> sortTutorsByExpertise(List<Tutor> tutors) {
        if (tutors == null || tutors.size() <= 1) {
            return tutors;
        }
        Tutor[] tutorArray = tutors.toArray(new Tutor[0]);
        mergeSort(tutorArray, 0, tutorArray.length - 1);
        return new ArrayList<>(Arrays.asList(tutorArray));
    }

    private void mergeSort(Tutor[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private void merge(Tutor[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Tutor[] leftArray = new Tutor[n1];
        Tutor[] rightArray = new Tutor[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // Put tutors with expertise first
            if (leftArray[i].getSubjectExpertise() && !rightArray[j].getSubjectExpertise()) {
                array[k] = leftArray[i];
                i++;
            } else if (!leftArray[i].getSubjectExpertise() && rightArray[j].getSubjectExpertise()) {
                array[k] = rightArray[j];
                j++;
            } else {
                // If expertise is the same, sort by rating (higher first)
                if (leftArray[i].getRating() >= rightArray[j].getRating()) {
                    array[k] = leftArray[i];
                    i++;
                } else {
                    array[k] = rightArray[j];
                    j++;
                }
            }
            k++;
        }

        // Copy remaining elements
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    /**
     * Sorts tutors by their rating
     */
    public List<Tutor> sortTutorsByRating(List<Tutor> tutors) {
        if (tutors == null || tutors.size() <= 1) {
            return tutors;
        }
        
        Tutor[] tutorArray = tutors.toArray(new Tutor[0]);
        mergeSortByRating(tutorArray, 0, tutorArray.length - 1);
        return new ArrayList<>(Arrays.asList(tutorArray));
    }
    
    private void mergeSortByRating(Tutor[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByRating(array, left, mid);
            mergeSortByRating(array, mid + 1, right);
            mergeByRating(array, left, mid, right);
        }
    }
    
    private void mergeByRating(Tutor[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Tutor[] leftArray = new Tutor[n1];
        Tutor[] rightArray = new Tutor[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // Sort by rating (higher rating first - descending order)
            if (leftArray[i].getRating() >= rightArray[j].getRating()) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    /**
     * Sorts tutors by their years of experience
     */
    public List<Tutor> sortTutorsByExperience(List<Tutor> tutors) {
        if (tutors == null || tutors.size() <= 1) {
            return tutors;
        }
        
        Tutor[] tutorArray = tutors.toArray(new Tutor[0]);
        mergeSortByExperience(tutorArray, 0, tutorArray.length - 1);
        return new ArrayList<>(Arrays.asList(tutorArray));
    }
    
    private void mergeSortByExperience(Tutor[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByExperience(array, left, mid);
            mergeSortByExperience(array, mid + 1, right);
            mergeByExperience(array, left, mid, right);
        }
    }
    
    private void mergeByExperience(Tutor[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Tutor[] leftArray = new Tutor[n1];
        Tutor[] rightArray = new Tutor[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // Sort by experience years (more experience first - descending order)
            if (leftArray[i].getExperienceYears() >= rightArray[j].getExperienceYears()) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    /**
     * Sorts tutors by experience years and then by rating
     */
    public List<Tutor> sortTutorsByExperienceAndRating(List<Tutor> tutors) {
        if (tutors == null || tutors.size() <= 1) {
            return tutors;
        }
        
        Tutor[] tutorArray = tutors.toArray(new Tutor[0]);
        mergeSortByExperienceAndRating(tutorArray, 0, tutorArray.length - 1);
        return new ArrayList<>(Arrays.asList(tutorArray));
    }

    private void mergeSortByExperienceAndRating(Tutor[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByExperienceAndRating(array, left, mid);
            mergeSortByExperienceAndRating(array, mid + 1, right);
            mergeByExperienceAndRating(array, left, mid, right);
        }
    }

    private void mergeByExperienceAndRating(Tutor[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Tutor[] leftArray = new Tutor[n1];
        Tutor[] rightArray = new Tutor[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // compare by experience years (more experience first)
            if (leftArray[i].getExperienceYears() > rightArray[j].getExperienceYears()) {
                array[k] = leftArray[i];
                i++;
            } else if (leftArray[i].getExperienceYears() < rightArray[j].getExperienceYears()) {
                array[k] = rightArray[j];
                j++;
            } else {
                // If experience years are equal, sort by rating (higher rating first)
                if (leftArray[i].getRating() >= rightArray[j].getRating()) {
                    array[k] = leftArray[i];
                    i++;
                } else {
                    array[k] = rightArray[j];
                    j++;
                }
            }
            k++;
        }

        // Copy remaining elements
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
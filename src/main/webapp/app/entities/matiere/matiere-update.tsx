import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHoraire } from 'app/shared/model/horaire.model';
import { getEntities as getHoraires } from 'app/entities/horaire/horaire.reducer';
import { IProf } from 'app/shared/model/prof.model';
import { getEntities as getProfs } from 'app/entities/prof/prof.reducer';
import { getEntity, updateEntity, createEntity, reset } from './matiere.reducer';
import { IMatiere } from 'app/shared/model/matiere.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMatiereUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MatiereUpdate = (props: IMatiereUpdateProps) => {
  const [idshoraire, setIdshoraire] = useState([]);
  const [idsprof, setIdsprof] = useState([]);
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { matiereEntity, horaires, profs, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/matiere');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getHoraires();
    props.getProfs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...matiereEntity,
        ...values,
        horaires: mapIdList(values.horaires),
        profs: mapIdList(values.profs),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="intranetApp.matiere.home.createOrEditLabel">
            <Translate contentKey="intranetApp.matiere.home.createOrEditLabel">Create or edit a Matiere</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : matiereEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="matiere-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="matiere-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idMatiereLabel" for="matiere-idMatiere">
                  <Translate contentKey="intranetApp.matiere.idMatiere">Id Matiere</Translate>
                </Label>
                <AvField
                  id="matiere-idMatiere"
                  type="string"
                  className="form-control"
                  name="idMatiere"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomMatiereLabel" for="matiere-nomMatiere">
                  <Translate contentKey="intranetApp.matiere.nomMatiere">Nom Matiere</Translate>
                </Label>
                <AvField
                  id="matiere-nomMatiere"
                  type="text"
                  name="nomMatiere"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="abreviationLabel" for="matiere-abreviation">
                  <Translate contentKey="intranetApp.matiere.abreviation">Abreviation</Translate>
                </Label>
                <AvField id="matiere-abreviation" type="text" name="abreviation" />
              </AvGroup>
              <AvGroup>
                <Label for="matiere-horaire">
                  <Translate contentKey="intranetApp.matiere.horaire">Horaire</Translate>
                </Label>
                <AvInput
                  id="matiere-horaire"
                  type="select"
                  multiple
                  className="form-control"
                  name="horaires"
                  value={matiereEntity.horaires && matiereEntity.horaires.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {horaires
                    ? horaires.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.idHoraire}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="matiere-prof">
                  <Translate contentKey="intranetApp.matiere.prof">Prof</Translate>
                </Label>
                <AvInput
                  id="matiere-prof"
                  type="select"
                  multiple
                  className="form-control"
                  name="profs"
                  value={matiereEntity.profs && matiereEntity.profs.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {profs
                    ? profs.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/matiere" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  horaires: storeState.horaire.entities,
  profs: storeState.prof.entities,
  matiereEntity: storeState.matiere.entity,
  loading: storeState.matiere.loading,
  updating: storeState.matiere.updating,
  updateSuccess: storeState.matiere.updateSuccess,
});

const mapDispatchToProps = {
  getHoraires,
  getProfs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MatiereUpdate);
